/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.lang.SuppressWarnings;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.RollerCommand;

public class OI {
  
  public static Joystick driverController = new Joystick(RobotMap.Controller.DRIVER_CONTROLLER.getValue());
  public static Joystick operatorController = new Joystick(RobotMap.Controller.OPERATOR_CONTROLLER.getValue());
  Button B_BUTTON = new JoystickButton(driverController, 2);


  @SuppressWarnings("resource")
  public OI() {
    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);

    B_BUTTON.whileHeld(new RollerCommand());

  }

  // Anything to be updated should be done in here
  public void updateOI() {

  }
}
