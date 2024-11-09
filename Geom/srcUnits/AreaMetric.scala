/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaMetric extends Any with Area with MetricUnits with TellDblBased
{ override def + (operand: Area): Area
  override def - (operand: Area): AreaMetric
  override def * (operand: Double): AreaMetric
  override def / (operand: Double): AreaMetric
  override def milesSqNum: Double = kilaresNum * Area.sqKmToMiles
}

/** Square metres  a measure of [[Area]]. */
class MetresSq(val metresSqNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Metres"
  override def unitsDbl: Double = metresSqNum
  override def endingStr: String = "m²"
  override def + (operand: Area): MetresSq = new MetresSq(metresSqNum + operand.metresSqNum)
  override def - (operand: Area): MetresSq = new MetresSq(metresSqNum - operand.metresSqNum)
  override def * (operand: Double): MetresSq = new MetresSq(metresSqNum * operand)
  def / (operand: Double): MetresSq = new MetresSq(metresSqNum / operand)
  override def kilaresNum: Double = metresSqNum / 1000000
  override def hectaresNum: Double = metresSqNum / 10000
}

object MetresSq
{ /** Factory apply method for constructing measurement of areas specified in square metres. */
  def apply(metresSqNum: Double): MetresSq = new MetresSq(metresSqNum)
}

/** Square kilometres a measure of [[Area]]. Kilares follows the same naming convention as Hectares. */
class Hectare(val hectaresNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Hectares"
  override def unitsDbl: Double = hectaresNum
  override def endingStr: String = "ha"
  override def +(operand: Area): Hectare = Hectare(hectaresNum + operand.hectaresNum)
  override def -(operand: Area): Hectare = Hectare(hectaresNum - operand.hectaresNum)
  override def *(operand: Double): Hectare = new Hectare(hectaresNum * operand)
  override def /(operand: Double): Hectare = new Hectare(hectaresNum / operand)
  override def kilaresNum: Double = hectaresNum / 100
  override def metresSqNum: Double = hectaresNum * 10000
}

/** Square kilometres a measure of [[Area]]. Kilares follows the same naming convention as [[Hectare]]s. */
class Kilare(val kilaresNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Kilares"
  override def unitsDbl: Double = kilaresNum
  override def endingStr: String = "km²"
  override def +(operand: Area): Kilare = Kilare(kilaresNum + operand.kilaresNum)
  override def -(operand: Area): Kilare = Kilare(kilaresNum - operand.kilaresNum)
  override def * (operand: Double): Kilare = new Kilare(kilaresNum * operand)
  override def / (operand: Double): Kilare = new Kilare(kilaresNum / operand)
  override def hectaresNum: Double = kilaresNum / 100
  override def metresSqNum: Double = kilaresNum * 10000
}

object Kilare
{ /** Factory apply method for creating units of square kilometres. */
  def apply(kMetresSqNum: Double): Kilare = new Kilare(kMetresSqNum)

  implicit class SequSumEv[A](thisSeq: Sequ[A])
  {
    def sumBy(f: A => Area): Kilare =
    { var acc: Double = 0
      thisSeq.foreach(a => acc += f(a).kilaresNum)
      Kilare(acc)
    }
  }
}

implicit class arraySumEv[A](thisArray: Array[A])
{
  def sumBy(f: A => Area): Kilare =
  {
    var acc: Double = 0
    thisArray.foreach(a => acc += f(a).kilaresNum)
    Kilare(acc)
  }
}