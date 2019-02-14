/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ClimbCommand extends Command {


  double winchMax = .2;
  double crankMax = .1;
  int timeoutMs = 10;
  public ClimbCommand() {
    requires(Robot.climb);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.climb.winch1.configNominalOutputForward(+0, timeoutMs);
    Robot.climb.winch1.configNominalOutputReverse(-0, timeoutMs);
    Robot.climb.winch1.configPeakOutputForward(+winchMax, timeoutMs);
    Robot.climb.winch1.configPeakOutputReverse(-winchMax, timeoutMs);

    
    Robot.climb.crank.configNominalOutputForward(+0, timeoutMs);
    Robot.climb.crank.configNominalOutputReverse(-0, timeoutMs);
    Robot.climb.crank.configPeakOutputForward(+crankMax, timeoutMs);
    Robot.climb.crank.configPeakOutputReverse(-crankMax, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    SmartDashboard.putNumber("WINCH MAX: ", winchMax);
    SmartDashboard.putNumber("CRANK MAX: ", crankMax);

    double winchValue = OI.operatorController.getRawAxis(1);
    double crankValue = OI.operatorController.getRawAxis(5);

    if(Math.abs(winchValue) < .05) {
      winchValue = 0;
    }

    if(Math.abs(crankValue) < .05) {
      crankValue = 0;
    }

    Robot.climb.setWinch(winchValue);
    Robot.climb.setCrank(crankValue);

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