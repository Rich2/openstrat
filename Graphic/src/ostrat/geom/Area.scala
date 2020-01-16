package ostrat
package geom

class Area(val metresSq: Double) extends AnyVal
{
  def + (op: Area): Area = new Area(metresSq + op.metresSq)
  def * (operand: Double): Area = new Area(metresSq * operand)
  def / (operand: Double): Area = new Area(metresSq / operand)
}

object Area
{

}

