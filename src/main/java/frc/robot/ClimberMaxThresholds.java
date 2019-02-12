package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.ClimbCommand;

public class ClimberMaxThresholds{
    
    //public SendableChooser winchChooser = new SendableChooser();

    public enum WinchPower{
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
    
    public enum CrankPower{
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
    
    public ClimberMaxThresholds(){
        
        Robot.winchChooser = new SendableChooser();
        Robot.winchChooser.addOption("10%", WinchPower.TEN_PERCENT);
        Robot.winchChooser.addOption("20%", WinchPower.TWENTY_PERCENT);
        Robot.winchChooser.addOption("30%", WinchPower.THIRTY_PERCENT);        
        Robot.winchChooser.addOption("40%", WinchPower.FORTY_PERCENT);
        Robot.winchChooser.addOption("50%", WinchPower.FIFTY_PERCENT);
        Robot.winchChooser.addOption("60%", WinchPower.SIXTY_PERCENT);        
        Robot.winchChooser.addOption("70%", WinchPower.SEVENTY_PERCENT);
        Robot.winchChooser.addOption("80%", WinchPower.EIGHTY_PERCENT);
        Robot.winchChooser.addOption("90%", WinchPower.NINETY_PERCENT);
        Robot.winchChooser.addOption("100%", WinchPower.HUNDRED_PERCENT);        

        SmartDashboard.putData("Winch Power", Robot.winchChooser);
    
        Robot.crankChooser = new SendableChooser();
        
        
        Robot.crankChooser.addOption("10%", CrankPower.TEN_PERCENT);
        Robot.crankChooser.addOption("20%", CrankPower.TWENTY_PERCENT);
        Robot.crankChooser.addOption("30%", CrankPower.THIRTY_PERCENT);        
        Robot.crankChooser.addOption("40%", CrankPower.FORTY_PERCENT);
        Robot.crankChooser.addOption("50%", CrankPower.FIFTY_PERCENT);
        Robot.crankChooser.addOption("60%", CrankPower.SIXTY_PERCENT);        
        Robot.crankChooser.addOption("70%", CrankPower.SEVENTY_PERCENT);
        Robot.crankChooser.addOption("80%", CrankPower.EIGHTY_PERCENT);
        Robot.crankChooser.addOption("90%", CrankPower.NINETY_PERCENT);
        Robot.crankChooser.addOption("100%", CrankPower.HUNDRED_PERCENT);        

        SmartDashboard.putData("Auto mode", Robot.crankChooser);
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        WinchPower selectedWinchPower = (WinchPower)(Robot.winchChooser.getSelected());
                    
        switch (selectedWinchPower){
            case TEN_PERCENT:
                new ClimbCommand(winchMax, crankMax)
            	Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .1;
            	break;
            case TWENTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .2;
            	break;
            case THIRTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .3;
            	break;
            case FORTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .4;
            	break;
            case FIFTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .5;
                break;
            case SIXTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .6;
                break;
            case SEVENTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .7;
                break;
            case EIGHTY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .8;
                break;
            case NINETY_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = .9;
                break;
            case HUNDRED_PERCENT:
                Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = 1;
                break;

            default:
                break;
            }
    }
}