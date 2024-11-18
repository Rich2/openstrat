/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaMetric extends Any with Area with MetricUnits with TellDblBased
{ override def + (operand: Area): Area
  override def - (operand: Area): AreaMetric
  override def * (operand: Double): AreaMetric
  override def / (operand: Double): AreaMetric
  override def acresNum: Double = hectaresNum * 0.404686
  override def milearesNum: Double = kilaresNum * Area.kilaresToMileares
  override def yardaresNum: Double = metraresNum * 1.19599
}

/** Square metres a measure of [[Area]]. Following convention this would be a called an Ares, but unfortunately and confusingly this is sometimes used for
 * 100m². */
class Metrares(val metraresNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Metres"
  override def unitsDbl: Double = metraresNum
  override def endingStr: String = "m²"
  override def + (operand: Area): Metrares = new Metrares(metraresNum + operand.metraresNum)
  override def - (operand: Area): Metrares = new Metrares(metraresNum - operand.metraresNum)
  override def * (operand: Double): Metrares = new Metrares(metraresNum * operand)
  def / (operand: Double): Metrares = new Metrares(metraresNum / operand)
  override def kilaresNum: Double = metraresNum / 1000000
  override def hectaresNum: Double = metraresNum / 10000
}

object Metrares
{ /** Factory apply method for constructing measurement of areas specified in square metres. */
  def apply(metresSqNum: Double): Metrares = new Metrares(metresSqNum)
}

/** Square kilometres a measure of [[Area]]. Kilares follows the same naming convention as Hectares. */
class Hectares(val hectaresNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Hectares"
  override def unitsDbl: Double = hectaresNum
  override def endingStr: String = "ha"
  override def +(operand: Area): Hectares = Hectares(hectaresNum + operand.hectaresNum)
  override def -(operand: Area): Hectares = Hectares(hectaresNum - operand.hectaresNum)
  override def *(operand: Double): Hectares = new Hectares(hectaresNum * operand)
  override def /(operand: Double): Hectares = new Hectares(hectaresNum / operand)
  override def kilaresNum: Double = hectaresNum / 100
  override def metraresNum: Double = hectaresNum * 10000
}

/** Square kilometres a measure of [[Area]]. Kilares follows the same naming convention as [[Hectares]]s. */
class Kilares(val kilaresNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Kilares"
  override def unitsDbl: Double = kilaresNum
  override def endingStr: String = "km²"
  override def +(operand: Area): Kilares = Kilares(kilaresNum + operand.kilaresNum)
  override def -(operand: Area): Kilares = Kilares(kilaresNum - operand.kilaresNum)
  override def * (operand: Double): Kilares = new Kilares(kilaresNum * operand)
  override def / (operand: Double): Kilares = new Kilares(kilaresNum / operand)
  override def hectaresNum: Double = kilaresNum / 100
  override def metraresNum: Double = kilaresNum * 10000
}

object Kilares
{ /** Factory apply method for creating units of square kilometres. */
  def apply(kMetresSqNum: Double): Kilares = new Kilares(kMetresSqNum)

  implicit class SequSumEv[A](thisSeq: Sequ[A])
  {
    def sumBy(f: A => Area): Kilares =
    { var acc: Double = 0
      thisSeq.foreach(a => acc += f(a).kilaresNum)
      Kilares(acc)
    }
  }
}

implicit class arraySumEv[A](thisArray: Array[A])
{
  def sumBy(f: A => Area): Kilares =
  {
    var acc: Double = 0
    thisArray.foreach(a => acc += f(a).kilaresNum)
    Kilares(acc)
  }
}