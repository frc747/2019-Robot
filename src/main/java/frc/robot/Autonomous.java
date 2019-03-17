package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.autonomous.*;




public class Autonomous{
    
    public enum AutoMode{
        AUTOMODE_NONE,
        AUTOMODE_TEST,
        AUTOMODE_FRONT_CARGO_LEFT,
        AUTOMODE_FRONT_CARGO_RIGHT,
        AUTOMODE_ROCKET_LEFT_LEVEL_TWO,
        AUTOMODE_ROCKET_RIGHT_LEVEL_TWO;
        ;
    }
    
    private SendableChooser autoChooser1;
    
    public Autonomous(){
        autoChooser1 = new SendableChooser();
        
        
        autoChooser1.addDefault("BUCKET HEAD (No autonomous)", AutoMode.AUTOMODE_NONE);
        // autoChooser1.addObject("Test Autonomous", AutoMode.AUTOMODE_TEST);
        autoChooser1.addObject("Front Cargoship Left", AutoMode.AUTOMODE_FRONT_CARGO_LEFT);
        autoChooser1.addObject("Front Cargoship Right", AutoMode.AUTOMODE_FRONT_CARGO_RIGHT);
        autoChooser1.addObject("Left Rocket, Level 2", AutoMode.AUTOMODE_ROCKET_LEFT_LEVEL_TWO);
        autoChooser1.addObject("Right Rocket, Level 2", AutoMode.AUTOMODE_ROCKET_RIGHT_LEVEL_TWO);

        SmartDashboard.putData("Auto mode", autoChooser1);
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        AutoMode selectedAutoMode = (AutoMode)(autoChooser1.getSelected());
                    
        switch (selectedAutoMode){
            case AUTOMODE_FRONT_CARGO_LEFT:
                new FrontFaceCargoAutoLeft().start();
                break;
            case AUTOMODE_FRONT_CARGO_RIGHT:
            	new FrontFaceCargoAutoRight().start();
                break;
            case AUTOMODE_ROCKET_LEFT_LEVEL_TWO:
            	new LeftRocketLevelTwo().start();
                break;
            case AUTOMODE_ROCKET_RIGHT_LEVEL_TWO:
            	new RightRocketLevelTwo().start();
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