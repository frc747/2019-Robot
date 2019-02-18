/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.maps;


/**
 * Drive Distances are in inches
 * All lift and climb positions are in ticks
 * Rotations are in degrees
 * Times are in seconds
 * Speed values are in motor %
 */
 
public class PositionConstants {
    private PositionConstants() {
    }

    public final class MeasurementConstants{
        
        private MeasurementConstants(){
        }
        
        public static final double ROBOT_LENGTH = 0.0;
        }


    public final class PIDGearTransfer{
    	
    	private PIDGearTransfer() {
    	}
    	
    	public static final double 	PICK_UP_POSITION = 9.012568279,
                                    START_POSITION = 4.018798828,
    								HOME_POSITION = 1.78369140625, //2.3232421875,
    								SCORE_POSITION = 0.0;
    }

}
