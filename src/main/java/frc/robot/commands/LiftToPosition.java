/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.liftcommands.*;
public class LiftToPosition extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LiftToPosition(double shoulder, double wrist) {
    requires(Robot.lift);

    addSequential(new PIDShoulderToPosition(shoulder));
    addParallel(new PIDWristToPosition(wrist));
  }
}
