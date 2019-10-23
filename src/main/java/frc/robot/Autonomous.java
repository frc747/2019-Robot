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
        AUTOMODE_RIGHT_FACE_CARGO_LEVEL_TWO,
        AUTOMODE_TWO_HATCH_FRONT_CARGO_LEFT,
        AUTOMODE_TWO_HATCH_FRONT_CARGO_RIGHT;
    }
    
    private SendableChooser<AutoMode> autoChooser1;
    
    public Autonomous(){
        autoChooser1 = new SendableChooser<AutoMode>();
        
        autoChooser1.setDefaultOption("BUCKET HEAD (No autonomous)", AutoMode.AUTOMODE_NONE);
        autoChooser1.addOption("Front Cargoship Left", AutoMode.AUTOMODE_FRONT_CARGO_LEFT);
        autoChooser1.addOption("Front Cargoship Right", AutoMode.AUTOMODE_FRONT_CARGO_RIGHT);
        autoChooser1.addOption("Two Hatch Front Cargoship Left", AutoMode.AUTOMODE_TWO_HATCH_FRONT_CARGO_LEFT);
        autoChooser1.addOption("Two Hatch Front Cargoship Right", AutoMode.AUTOMODE_TWO_HATCH_FRONT_CARGO_RIGHT);
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
                OI.table.getEntry("pipeline").setDouble(1.0);
                new FrontFaceCargoShipLeft().start();
                Robot.side = "left";
                break;
            case AUTOMODE_FRONT_CARGO_RIGHT:
                OI.table.getEntry("pipeline").setDouble(2.0);
                new FrontFaceCargoShipRight().start();
                Robot.side = "right";
                break;
            case AUTOMODE_TWO_HATCH_FRONT_CARGO_LEFT:
                OI.table.getEntry("pipeline").setDouble(1.0);
                new FrontFaceCargoShipLeft().start();
                Robot.side = "leftTwo";
                break;
            case AUTOMODE_TWO_HATCH_FRONT_CARGO_RIGHT:
                OI.table.getEntry("pipeline").setDouble(2.0);
                new FrontFaceCargoShipRight().start();
                Robot.side = "rightTwo";
                break;
            case AUTOMODE_ROCKET_LEFT_LEVEL_TWO:
                OI.table.getEntry("pipeline").setDouble(0.0);
            	new LeftRocketLevelTwo().start();
                break;
            case AUTOMODE_ROCKET_RIGHT_LEVEL_TWO:
                OI.table.getEntry("pipeline").setDouble(0.0);
            	new RightRocketLevelTwo().start();
                break;
            case AUTOMODE_LEFT_FACE_CARGO_LEVEL_TWO:
                // want to focus on the rightmost target on the side face of the cargoship
                OI.table.getEntry("pipeline").setDouble(2.0);
                new LeftFaceCargoShipLevelTwo().start();
                break;
            case AUTOMODE_RIGHT_FACE_CARGO_LEVEL_TWO:
                // want to focus on the leftmost target on the side face of the cargoship
                OI.table.getEntry("pipeline").setDouble(1.0);
                new RightFaceCargoShipLevelTwo().start();
                break;
            case AUTOMODE_NONE:
                //DO NOTHING
                OI.table.getEntry("pipeline").setDouble(0.0);
                Robot.operatorControl = true;
            default:
                break;
            }
    }
}