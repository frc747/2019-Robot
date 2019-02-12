package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class ClimbCrankMaxThreshold{
    
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
    
    //public SendableChooser crankChooser;
    
    public ClimbCrankMaxThreshold(){
        Robot.crankChooser = new SendableChooser();
        
        
        Robot.crankChooser.addOption("10%", AutoMode.TEN_PERCENT);
        Robot.crankChooser.addOption("20%", AutoMode.TWENTY_PERCENT);
        Robot.crankChooser.addOption("30%", AutoMode.THIRTY_PERCENT);        
        Robot.crankChooser.addOption("40%", AutoMode.FORTY_PERCENT);
        Robot.crankChooser.addOption("50%", AutoMode.FIFTY_PERCENT);
        Robot.crankChooser.addOption("60%", AutoMode.SIXTY_PERCENT);        
        Robot.crankChooser.addOption("70%", AutoMode.SEVENTY_PERCENT);
        Robot.crankChooser.addOption("80%", AutoMode.EIGHTY_PERCENT);
        Robot.crankChooser.addOption("90%", AutoMode.NINETY_PERCENT);
        Robot.crankChooser.addOption("100%", AutoMode.HUNDRED_PERCENT);        

        SmartDashboard.putData("Auto mode", Robot.crankChooser);
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        AutoMode selectedAutoMode = (AutoMode)(Robot.crankChooser.getSelected());
                    
        switch (selectedAutoMode){
            case TEN_PERCENT:
            	Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .1;
            	break;
            case TWENTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .2;
            	break;
            case THIRTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .3;
            	break;
            case FORTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .4;
            	break;
            case FIFTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .5;
                break;
            case SIXTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .6;
                break;
            case SEVENTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .7;
                break;
            case EIGHTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .8;
                break;
            case NINETY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = .9;
                break;
            case HUNDRED_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = 1;
                break;

            default:
                break;
            }
    }
}