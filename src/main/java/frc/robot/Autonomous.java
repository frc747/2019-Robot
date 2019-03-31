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
        AUTOMODE_ROCKET_RIGHT_LEVEL_TWO,
        AUTOMODE_LEFT_FACE_CARGO_LEVEL_TWO,
        AUTOMODE_RIGHT_FACE_CARGO_LEVEL_TWO;
    }
    
    private SendableChooser<AutoMode> autoChooser1;
    
    public Autonomous(){
        autoChooser1 = new SendableChooser<AutoMode>();
        
        autoChooser1.setDefaultOption("BUCKET HEAD (No autonomous)", AutoMode.AUTOMODE_NONE);
        autoChooser1.addOption("Front Cargoship Left", AutoMode.AUTOMODE_FRONT_CARGO_LEFT);
        autoChooser1.addOption("Front Cargoship Right", AutoMode.AUTOMODE_FRONT_CARGO_RIGHT);
        autoChooser1.addOption("Left Rocket, Level 2", AutoMode.AUTOMODE_ROCKET_LEFT_LEVEL_TWO);
        autoChooser1.addOption("Right Rocket, Level 2", AutoMode.AUTOMODE_ROCKET_RIGHT_LEVEL_TWO);
        autoChooser1.addOption("Left Face Cargo, Level 2", AutoMode.AUTOMODE_LEFT_FACE_CARGO_LEVEL_TWO);
        autoChooser1.addOption("Right Face Cargo, Level 2", AutoMode.AUTOMODE_RIGHT_FACE_CARGO_LEVEL_TWO);
        SmartDashboard.putData("Auto mode", autoChooser1);
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        AutoMode selectedAutoMode = (AutoMode)(autoChooser1.getSelected());
                    
        switch (selectedAutoMode){
            case AUTOMODE_FRONT_CARGO_LEFT:
                new FrontFaceCargoShipLeft().start();
                break;
            case AUTOMODE_FRONT_CARGO_RIGHT:
            	new FrontFaceCargoShipRight().start();
                break;
            case AUTOMODE_ROCKET_LEFT_LEVEL_TWO:
            	new LeftRocketLevelTwo().start();
                break;
            case AUTOMODE_ROCKET_RIGHT_LEVEL_TWO:
            	new RightRocketLevelTwo().start();
                break;
            case AUTOMODE_LEFT_FACE_CARGO_LEVEL_TWO:
                new LeftFaceCargoShipLevelTwo().start();
                break;
            case AUTOMODE_RIGHT_FACE_CARGO_LEVEL_TWO:
                new RightFaceCargoShipLevelTwo().start();
                break;
            case AUTOMODE_NONE:
                //DO NOTHING
                Robot.operatorControl = true;
            default:
                break;
            }
    }
}