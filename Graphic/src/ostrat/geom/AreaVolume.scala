package ostrat
package geom

class Area(val metresSq: Double) extends AnyVal
{ def + (op: Area): Area = new Area(metresSq + op.metresSq)
  def - (op: Area): Area = new Area(metresSq - op.metresSq)
  def * (operand: Double): Area = new Area(metresSq * operand)
  def / (operand: Double): Area = new Area(metresSq / operand)
}

class Volume(val metresCubed: Double) extends AnyVal
{ def + (op: Volume): Volume = new Volume(metresCubed + op.metresCubed)
  def - (op: Volume): Volume = new Volume(metresCubed - op.metresCubed)
  def * (operand: Double): Volume = new Volume(metresCubed * operand)
  def / (operand: Double): Volume = new Volume(metresCubed / operand)
}

