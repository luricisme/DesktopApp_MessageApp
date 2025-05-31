package events;

public class PublicEvent {
    private static PublicEvent instance;
    private EventImageView eventImageView;
    private EventChat eventChat;
    private EventLogin eventLogin;
    private EventMain eventMain;
    
    public static PublicEvent getInstance(){
        if(instance == null){
            instance = new PublicEvent();
        }
        return instance;
    }
    
    private PublicEvent(){
        
    }
    
    public void addEventMain(EventMain event){
        this.eventMain = event;
    }
    
    public EventMain getEventMain(){
        return eventMain;
    }
    
    public void addEventImageView(EventImageView event){
        this.eventImageView = event;
    }
    
    public EventImageView getEventImageView(){
        return eventImageView;
    }
    
    public void addEventChat(EventChat event){
        this.eventChat = event;
    }
    
    public EventChat getEventChat(){
        return eventChat;
    }
    
    public void addEventLogin(EventLogin event){
        this.eventLogin = event;
    }
    
    public EventLogin getEventLogin(){
        return eventLogin;
    }
}
