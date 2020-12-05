package Actor;


import DataController.DataHandler;
import Misc.ActorType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer extends Actor {
    @Override
    public ActorType GetActorType()
    {
        return ActorType.CUSTOMER;
    }
    //#region Core Methods
    @Override
    public Actor SignIn(String id, String password) {
        return DataHandler.GetInstance().SignIn((conn)->{
            String sql = "select * from customer where id = ? and password = ?";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,id);
                stmt.setString(2,password);
                ResultSet resultSet = stmt.executeQuery();
                Actor actor = null;
                if(resultSet.next())
                {
                    this.account.ID = resultSet.getString("id");
                    this.account.password = resultSet.getString("password");
                    this.name = resultSet.getString("name");
                    this.phoneNumber = resultSet.getString("phone_number");
                    this.address = resultSet.getString("address");
                    this.age = Integer.valueOf(resultSet.getInt("age")).byteValue();
                    this.gender = resultSet.getString("gender");
                    actor = this;
                }
                // Close resource
                stmt.close();
                conn.close();
                return actor;
            } catch (SQLException exc) { }
            return null;
        });
    }
    @Override
    public Actor SignUp(String id,String password,String name,String phoneNumber,String address,int age,String gender){
        return DataHandler.GetInstance().SignUp((conn)->{
            String sql = "select id from customer where id = ?";
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,id);
                var resultSet = stmt.executeQuery();
                if(resultSet.next())
                    return null;
                sql = "insert into customer values(?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                InitData(id, password, name, phoneNumber, address, age, gender);
                stmt.setString(1,id);
                stmt.setString(2,password);
                stmt.setString(3,name);
                stmt.setString(4,phoneNumber);
                stmt.setString(5,address);
                stmt.setInt(6,age);
                stmt.setString(7,gender);
                stmt.executeUpdate();
                // Close resource
                conn.commit();
                stmt.close();
                conn.close();
                return this;
            } catch (SQLException exc) {
                try {
                    conn.rollback();
                } catch (SQLException exception) { }
                System.out.println("Error: " + exc.getMessage());
            }
            return null;
        });
    }
    @Override
    public void Display() {
        System.out.println("ID: " + account.ID);
        System.out.println("Password: " + account.password);
        System.out.println("Name: " + name);
        System.out.println("Phone number: " + phoneNumber);

    }
    //#endregion

    //#region Private Methods
    private void InitData(String id,String password,String name,String phoneNumber,String address,int age,String gender)
    {
        this.account.ID = id;
        this.account.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.age = Integer.valueOf(age).byteValue();
        this.gender = gender;
    }
    //#endregion
}
