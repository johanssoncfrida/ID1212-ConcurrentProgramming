package kth.bookdesk.controller;

import kth.bookdesk.model.Booker;
import kth.bookdesk.model.Desk;
import kth.bookdesk.model.Logger;
import kth.bookdesk.model.User;
import kth.bookdesk.repository.BookerRepository;
import kth.bookdesk.repository.DeskRepository;
import kth.bookdesk.repository.LoggerRepository;
import kth.bookdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    User user;
    Date date;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeskRepository deskRepository;

    @Autowired
    BookerRepository bookerRepository;

    @Autowired
    LoggerRepository loggerRepository;

    @GetMapping("/")
    public String index(Model model) throws IOException {
        String info = getInfo();
        model.addAttribute("info", info);
        return "index";
    }

    @GetMapping("signUp")
    public String signUp() {
        return "signUp";
    }

    @PostMapping("adduser")
    public ModelAndView addUser(ModelAndView mv, Model model, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
                          @RequestParam("id") String id, @RequestParam("mobile") String mobile,
                          @RequestParam("email") String email, @RequestParam("username") String username,
                          @RequestParam("password") String password) {
        List<User> users = userRepository.findAll();
        if(checkUsers(users, username, email) == 0){
            User user = new User(firstname, lastname, id, mobile, email, username, password);
            userRepository.save(user);
            mv.setViewName("index");
        }else if(checkUsers(users, username, email) == 1){
            model.addAttribute("userError", "Username taken");
            mv.setViewName("signUp");
        }else{
            model.addAttribute("userError", "Email taken");
            mv.setViewName("signUp");
        }

        return mv;
    }


    @PostMapping("signIn")
    public ModelAndView signIn(ModelAndView mv, Model model, @RequestParam("username") String username, @RequestParam("email") String email,
                               @RequestParam("password") String password) {

        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getUserName().equals(username) && u.getPassword().equals(password)) {
                mv = new ModelAndView("desks");
                user = u;

                long millis=System.currentTimeMillis();
                date = new Date(millis);


                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 1);
                java.util.Date d = cal.getTime();
                Date maxBookingDate = new Date(d.getTime());

                Timestamp ts = new Timestamp(date.getTime());
                List<Desk> desks = deskRepository.findAll();
                desks = checkDesks(desks);
                desks = populateList(desks,ts);

                Booker booker = bookerRepository.getById(user.getIdBooker());
                if(booker == null){}
                else{
                    model.addAttribute("booker", booker);
                    for(Desk desk: desks){
                        if(desk.getPk() == booker.getDeskId()){
                            model.addAttribute("bookersDesk",desk);
                        }
                    }
                }
                model.addAttribute("maxBookingDate", maxBookingDate);
                model.addAttribute("pickeddate", date);
                model.addAttribute("desks", desks);
                return mv;
            }
        }
        mv = new ModelAndView("index");
        return mv;
    }

    @PostMapping("chooseday")
    public ModelAndView chooseDay(ModelAndView mv, Model model, @RequestParam("chooseday") String d, @RequestParam(value = "getBookingInfo", required = false) String getInfo)  {

        mv.setViewName("desks");
        date = Date.valueOf(d);
        List<Desk> desks = deskRepository.findAll();
        Booker booker = bookerRepository.getById(user.getIdBooker());
        if(booker == null){}
        else{
            model.addAttribute("booker", booker);
            for(Desk desk: desks){
                if(desk.getPk() == booker.getDeskId()){
                    model.addAttribute("bookersDesk",desk);
                }
            }
        }
        Timestamp ts = new Timestamp(date.getTime());
        desks = populateList(desks,ts);

        if(getInfo != null){
            List<Logger> logs = loggerRepository.findAll();
            logs = getLogs(logs, ts);
            if(logs != null){
                model.addAttribute("logs", logs);
                if(logs.size() == 1){
                    model.addAttribute("logsize", logs.size() + " booking made this day.");
                }else{
                    model.addAttribute("logsize", logs.size() + " bookings made this day.");
                }
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        java.util.Date maxD = cal.getTime();
        Date maxBookingDate = new Date(maxD.getTime());

        model.addAttribute("maxBookingDate", maxBookingDate);
        model.addAttribute("pickeddate", date);
        model.addAttribute("desks", desks);
        return mv;
    }

    @PostMapping("bookdesk")
    public ModelAndView bookDesk(ModelAndView mv, Model model, @RequestParam(value="deskid",required = false) Integer deskId,
                                 @RequestParam(value="date",required = false) String d, @RequestParam(value="company",required = false) String company,
                                 @RequestParam(value="starttime",required = false) String start, @RequestParam(value="endtime", required = false) String end,
                                 @RequestParam(value="canceldeskid", required=false) Integer desktocancel) throws ParseException {

        if(deskId != null) {
            //Check booker
            Booker booker = bookerRepository.getById(user.getIdBooker());
            if(booker != null){
                model.addAttribute("errBooker", "Oh, how the tables have turned. You are only allowed to have one booking at a time.");
            }else{
                //Fix desk
                Timestamp stampstart = getTSBook(d, start);
                Timestamp stampend = getTSBook(d, end);
                if(stampstart == null || stampend == null){
                    model.addAttribute("errorParse", "Unfortunately, we have an error with our page, try again later");
                    mv.setViewName("index");
                }
                Desk desk = new Desk(deskId,stampstart,stampend,1);
                deskRepository.save(desk);

                //Fix booker
                Booker bookerOk = new Booker(user.getIdBooker(), company, desk.getPk(), user, desk);
                bookerRepository.save(bookerOk);

                //fix logger
                Timestamp logDate = new Timestamp(System.currentTimeMillis());
                Logger logger = new Logger(bookerOk.getId(), logDate);
                loggerRepository.save(logger);
            }
        }else{
            bookerRepository.deleteById(user.getIdBooker());
            deskRepository.deleteById(desktocancel);

        }
        Booker booker = bookerRepository.getById(user.getIdBooker());

        if(booker == null){}
        else{
            model.addAttribute("booker", booker);
        }
            model.addAttribute("pickeddate", date);

        mv.setViewName("desks");
        List<Desk> desks = deskRepository.findAll();
        Timestamp ts = new Timestamp(date.getTime());
        desks = populateList(desks, ts);
        model.addAttribute("desks", desks);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        java.util.Date maxD = cal.getTime();
        Date maxBookingDate = new Date(maxD.getTime());

        model.addAttribute("maxBookingDate", maxBookingDate);

        return mv;
    }

    /*
    * Create Timestamp from String date(d) and String time(t)
    * */
    private Timestamp getTSBook(String d, String t) {
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String concat = d + " " + t + ":00";
            java.util.Date parsedDate = dateFormat.parse(concat);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            return timestamp;
        }catch(ParseException ex){
            return null;
        }
    }

    /*
    * Filter list from other desks so only 10 desks for todays date is collected.
    * */
    private List<Desk> populateList(List<Desk> list, Timestamp chosenday){
        if(list.isEmpty()){
            for(int i = 0; i < 10; i++){
                list.add(new Desk(i+1));
            }
            return list;
        }else{
            List<Desk> desks = new ArrayList<>(10);
            for (int i = 0; i < 10; i++) {
                desks.add(new Desk(-1));
            }
            for(int i = 0; i < list.size(); i++){
                if(i < 0 || i >= list.size()){
                    for(int j = 0; j < desks.size(); j++) {
                        if (desks.get(j).getDeskId() == -1) {
                            desks.set(j, new Desk(j + 1));
                            break;
                        }
                    }
                }else if(getDate(chosenday, list.get(i).getEndTime()) == 1 || getDate(chosenday, list.get(i).getEndTime()) == 2){}
                else if(getDate(chosenday, list.get(i).getEndTime()) == 0 && !desks.contains(list.get(i))){
                    Desk desk = list.get(i);
                    int pos = desk.getDeskId();
                    desks.set(pos-1, desk);
                }else{
                    desks.add(new Desk(i+1));
                }
            }

            for(int i = 0; i < 10; i++){
                if(desks.get(i).getDeskId() == -1){
                    desks.set(i, new Desk(i+1));
                }
            }
            return desks;
        }
    }

    /*
    * Gets logs and filter to get ts date
    * */
    public List<Logger> getLogs(List<Logger> logs, Timestamp ts){
        List<Logger> todaysLogs = new ArrayList<>();
        if(logs.isEmpty()){
            return null;
        }else{
            for(Logger log : logs){
                if(getDate(ts, log.getDate()) == 1){}
                else if(getDate(ts, log.getDate()) == 0){
                    todaysLogs.add(log);
                }
            }
            return todaysLogs;
        }
    }

    /*
    * Extract date from timestamp
    * */
    private int getDate(Timestamp today, Timestamp fromlist){
        SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
        String datetoday = sdfdate.format(today);
        String datelist = sdfdate.format(fromlist);
        if(datetoday.compareTo(datelist) < 0) {
            return 1;
        }else if(datetoday.compareTo(datelist) > 0){
            return 2;
        }
        else{
            return 0;

        }
    }

    /*
    * Checks desks so no old desk bookings are in database
    * */
    public List<Desk> checkDesks(List<Desk> desks){
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        Timestamp today = new Timestamp(date.getTime());
        List<Desk> list = new ArrayList<>();
        for(Desk d: desks){
            if(getDate(today,d.getEndTime()) == 2){
                //remove old bookings from list
                bookerRepository.removeBookedDesk(d.getPk());
                deskRepository.deleteById(d.getPk());
            }else{
                list.add(d);
            }
        }
        return list;
    }

    /*
    * Checks for duplicate usernames or emails
    * */
    private int checkUsers(List<User> users, String uname, String email){
        for(User u: users){
            if(u.getUserName().equals(uname)) return 1;
            if(u.getEmail().equals(email)) return 2;
        }
        return 0;
    }

    /*
    * Gets Info from /resources/static/file.txt that shows on index file
    * */
    private String getInfo() throws IOException {

        String everything = "";
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader("src/main/resources/static/info.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
            return everything;
        }catch(Exception ex){
            return everything;
        }
    }

}
