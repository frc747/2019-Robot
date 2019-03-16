package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.autonomous.*;




public class Autonomous{
    
    public enum AutoMode{
        AUTOMODE_NONE,
        AUTOMODE_TEST,
        AUTOMODE_FRONT_LEFT,
        AUTOMODE_FRONT_RIGHT;
    }
    
    private SendableChooser autoChooser1;
    
    public Autonomous(){
        autoChooser1 = new SendableChooser();
        
        
        autoChooser1.addDefault("No autonomous", AutoMode.AUTOMODE_NONE);
        // autoChooser1.addObject("Test Autonomous", AutoMode.AUTOMODE_TEST);
        autoChooser1.addObject("Front Cargoship Left", AutoMode.AUTOMODE_FRONT_LEFT);
        autoChooser1.addObject("Front Cargoship Right", AutoMode.AUTOMODE_FRONT_RIGHT);

        SmartDashboard.putData("Auto mode", autoChooser1);
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        AutoMode selectedAutoMode = (AutoMode)(autoChooser1.getSelected());
                    
        switch (selectedAutoMode){
            case AUTOMODE_FRONT_LEFT:
                new FrontFaceCargoAutoLeft().start();
                break;
            case AUTOMODE_FRONT_RIGHT:
            	new FrontFaceCargoAutoRight().start();
            	break;
            // case AUTOMODE_TEST:
            // 	new TestCommandGroup().start();
            // 	break;
            case AUTOMODE_NONE:
                //DO NOTHING
            default:
                break;
            }
    }
}