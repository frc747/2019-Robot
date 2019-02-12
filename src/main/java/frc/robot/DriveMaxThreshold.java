package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.commands.DriveCommand;




public class DriveMaxThreshold{
    
    public enum AutoMode{
        TEN_PERCENT,
        TWENTY_PERCENT,
        THIRTY_PERCENT,
        FORTY_PERCENT,
        FIFTY_PERCENT,
        SIXTY_PERCENT,
        SEVENTY_PERCENT,
        EIGHTY_PERCENT,
        NINETY_PERCENT,
        HUNDRED_PERCENT
    }
    
    public DriveMaxThreshold(){
        //Robot.driveChooser = new SendableChooser();
        
        
        Robot.driveChooser.addOption("10%", AutoMode.TEN_PERCENT);
        Robot.driveChooser.addOption("20%", AutoMode.TWENTY_PERCENT);
        Robot.driveChooser.addOption("30%", AutoMode.THIRTY_PERCENT);        
        Robot.driveChooser.addOption("40%", AutoMode.FORTY_PERCENT);
        Robot.driveChooser.addOption("50%", AutoMode.FIFTY_PERCENT);
        Robot.driveChooser.addOption("60%", AutoMode.SIXTY_PERCENT);        
        Robot.driveChooser.addOption("70%", AutoMode.SEVENTY_PERCENT);
        Robot.driveChooser.addOption("80%", AutoMode.EIGHTY_PERCENT);
        Robot.driveChooser.addOption("90%", AutoMode.NINETY_PERCENT);
        Robot.driveChooser.addOption("100%", AutoMode.HUNDRED_PERCENT);        

        SmartDashboard.putData("Auto mode", Robot.driveChooser);
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        AutoMode selectedAutoMode = (AutoMode)(Robot.driveChooser.getSelected());
                    
        switch (selectedAutoMode){
            case TEN_PERCENT:
                DriveCommand.maxThreshold = .1;
            	break;
            case TWENTY_PERCENT:
                DriveCommand.maxThreshold = .2;
            	break;
            case THIRTY_PERCENT:
                DriveCommand.maxThreshold = .3;
            	break;
            case FORTY_PERCENT:
                DriveCommand.maxThreshold = .4;
            	break;
            case FIFTY_PERCENT:
                DriveCommand.maxThreshold = .5;
                break;
            case SIXTY_PERCENT:
                DriveCommand.maxThreshold = .6;
                break;
            case SEVENTY_PERCENT:
                DriveCommand.maxThreshold = .7;
                break;
            case EIGHTY_PERCENT:
                DriveCommand.maxThreshold = .8;
                break;
            case NINETY_PERCENT:
                DriveCommand.maxThreshold = .9;
                break;
            case HUNDRED_PERCENT:
                DriveCommand.maxThreshold = 1;
                break;

            default:
                break;
            }
    }
}