/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI;

public class ClimbCommand extends Command {

  double winch;
  double crank;
  
  boolean maxIsSet;

  public ClimbCommand(double winchMax, double crankMax) {
    Robot.CLIMB_SUBSYSTEM.maxThresholdWinch = winchMax;
    Robot.CLIMB_SUBSYSTEM.maxThresholdCrank = crankMax;
  }

  public ClimbCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.CLIMB_SUBSYSTEM.climberCrank.configNominalOutputForward(0, 10);
    Robot.CLIMB_SUBSYSTEM.climberCrank.configNominalOutputReverse(0, 10);
    Robot.CLIMB_SUBSYSTEM.climberCrank.configPeakOutputForward(Robot.CLIMB_SUBSYSTEM.maxThresholdCrank, 10);
    Robot.CLIMB_SUBSYSTEM.climberCrank.configPeakOutputReverse(-Robot.CLIMB_SUBSYSTEM.maxThresholdCrank, 10);

    Robot.CLIMB_SUBSYSTEM.climberWinchPrimary.configNominalOutputForward(0, 10);
    Robot.CLIMB_SUBSYSTEM.climberWinchPrimary.configNominalOutputReverse(0, 10);
    Robot.CLIMB_SUBSYSTEM.climberWinchPrimary.configPeakOutputForward(Robot.CLIMB_SUBSYSTEM.maxThresholdWinch, 10);
    Robot.CLIMB_SUBSYSTEM.climberWinchPrimary.configPeakOutputReverse(-Robot.CLIMB_SUBSYSTEM.maxThresholdWinch, 10);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {


      winch = OI.operatorController.getRawAxis(1);
      crank = OI.operatorController.getRawAxis(5);
  
      Robot.CLIMB_SUBSYSTEM.setWinch(winch);
      Robot.CLIMB_SUBSYSTEM.setCrank(crank);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
