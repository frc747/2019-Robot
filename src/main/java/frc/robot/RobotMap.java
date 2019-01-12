/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  public RobotMap() {
    
  }
  public static enum Controller {
    DRIVER_CONTROLLER(0),
    OPERATOR_CONTROLLER(1),
    BUTTON_A(1),
    BUTTON_B(2),
    BUTTON_X(3),
    BUTTON_Y(4),
    BUTTON_LB(5),
    BUTTON_RB(6),
    BUTTON_BACK(7),
    BUTTON_START(8),
    STICK_LEFT(9),
    STICK_RIGHT(10);
    

    private int value;

    private Controller(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

}
