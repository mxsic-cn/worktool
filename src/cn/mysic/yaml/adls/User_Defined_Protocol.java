package cn.mysic.yaml.adls;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchuan on 9/27/16.
 */
public class User_Defined_Protocol {
   private List<Protocol> user_defined_protocol;

   public User_Defined_Protocol() {
      this.user_defined_protocol = new ArrayList<Protocol>();
      this.user_defined_protocol.add(new Protocol());
      this.user_defined_protocol.add(new Protocol());
      this.user_defined_protocol.add(new Protocol());
   }

   public List<Protocol> getUser_defined_protocol() {
      return user_defined_protocol;
   }

   public void setUser_defined_protocol(List<Protocol> user_defined_protocol) {
      this.user_defined_protocol = user_defined_protocol;
   }
}
