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
    LEFT_STICK(0),
    RIGHT_STICK(1),
    OPERATOR_CONTROLLER(2),
    TEST_CONTROLLER(3);

    private int value;

    private Controller(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

}
