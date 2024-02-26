package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants.HangingConstants;

public class Hanging extends SubsystemBase {
   /* private final CANSparkMax m_hangingLeftMotor =  new CANSparkMax(HangingConstants.kHangingLeftMotor, MotorType.kBrushless);
    private final CANSparkMax m_hangingRightMotor =  new CANSparkMax(HangingConstants.kHangingRightMotor, MotorType.kBrushless);

    public Hang() {
        m_hangingLeftMotor.follow(m_hangingRightMotor);
        m_hangingLeftMotor.setInverted(true);
    }

    public Command lift() {
        return this.startEnd(() -> m_hangingRightMotor.set(0.4), () -> m_hangingRightMotor.set(0));
    }

    public Command hang(){
        return this.startEnd(() -> m_hangingRightMotor.set(0.4), () -> m_hangingRightMotor.set(0));
    }
*/
}
