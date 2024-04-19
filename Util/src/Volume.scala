/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Not sure about this class. */
class Volume(val metresCubed: Double) extends AnyVal
{ def + (op: Volume): Volume = new Volume(metresCubed + op.metresCubed)
  def - (op: Volume): Volume = new Volume(metresCubed - op.metresCubed)
  def * (operand: Double): Volume = new Volume(metresCubed * operand)
  def / (operand: Double): Volume = new Volume(metresCubed / operand)
}