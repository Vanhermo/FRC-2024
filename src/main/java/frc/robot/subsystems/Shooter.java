package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants.ShooterConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;

public class Shooter extends SubsystemBase {

   /* private final CANSparkMax m_shooterTopMotor = new CANSparkMax(ShooterConstants.kShooterTopMotor, MotorType.kBrushed);
    private final CANSparkMax m_shooterBottomMotor =  new CANSparkMax(ShooterConstants.kShooterBottomMotor, MotorType.kBrushed);

    public Shooter() {
        m_shooterBottomMotor.follow(m_shooterTopMotor);
    }

    public Command shoot() {
        return this.startEnd(() -> m_shooterTopMotor.set(1), () -> m_shooterTopMotor.set(0));
    }

    public Command take(){
        return this.startEnd(() -> m_shooterTopMotor.set(-0.4), () -> m_shooterTopMotor.set(0));
    }
    */
}
