/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.*;
import frc.robot.OI;

/**
 * Add your docs here.
 */
public class JoystickRumble extends TimedCommand {
  /**
   * Add your docs here.
   */
  public JoystickRumble(double timeout) {
    super(timeout);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //sets the joystick of choice to rumble (side of stick, on/off)
    OI.driverController.set(kLeftRumble, 1);
    OI.driverController.set(kRightRumble, 1);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Called once after timeout
  @Override
  protected void end() {
    OI.driverController.set(kLeftRumble, 0);
    OI.driverController.set(kRightRumble, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
