/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaMetric extends Any with Area with MetricUnits with TellDblBased
{ override def + (operand: Area): Area
  override def - (operand: Area): AreaMetric
  override def * (operand: Double): AreaMetric
  override def / (operand: Double): AreaMetric
  override def mileareNum: Double = kilareNum * Area.kilareToMileare
}

/** Square metres a measure of [[Area]]. Following convention this would be a called an Are,  ubt unfortuntely and confusingly this is sometimes used for
 * 100m². */
class Metrare(val metrareNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Metres"
  override def unitsDbl: Double = metrareNum
  override def endingStr: String = "m²"
  override def + (operand: Area): Metrare = new Metrare(metrareNum + operand.metrareNum)
  override def - (operand: Area): Metrare = new Metrare(metrareNum - operand.metrareNum)
  override def * (operand: Double): Metrare = new Metrare(metrareNum * operand)
  def / (operand: Double): Metrare = new Metrare(metrareNum / operand)
  override def kilareNum: Double = metrareNum / 1000000
  override def hectareNum: Double = metrareNum / 10000
}

object Metrare
{ /** Factory apply method for constructing measurement of areas specified in square metres. */
  def apply(metresSqNum: Double): Metrare = new Metrare(metresSqNum)
}

/** Square kilometres a measure of [[Area]]. Kilares follows the same naming convention as Hectares. */
class Hectare(val hectareNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Hectares"
  override def unitsDbl: Double = hectareNum
  override def endingStr: String = "ha"
  override def +(operand: Area): Hectare = Hectare(hectareNum + operand.hectareNum)
  override def -(operand: Area): Hectare = Hectare(hectareNum - operand.hectareNum)
  override def *(operand: Double): Hectare = new Hectare(hectareNum * operand)
  override def /(operand: Double): Hectare = new Hectare(hectareNum / operand)
  override def kilareNum: Double = hectareNum / 100
  override def metrareNum: Double = hectareNum * 10000
}

/** Square kilometres a measure of [[Area]]. Kilares follows the same naming convention as [[Hectare]]s. */
class Kilare(val kilareNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Kilares"
  override def unitsDbl: Double = kilareNum
  override def endingStr: String = "km²"
  override def +(operand: Area): Kilare = Kilare(kilareNum + operand.kilareNum)
  override def -(operand: Area): Kilare = Kilare(kilareNum - operand.kilareNum)
  override def * (operand: Double): Kilare = new Kilare(kilareNum * operand)
  override def / (operand: Double): Kilare = new Kilare(kilareNum / operand)
  override def hectareNum: Double = kilareNum / 100
  override def metrareNum: Double = kilareNum * 10000
}

object Kilare
{ /** Factory apply method for creating units of square kilometres. */
  def apply(kMetresSqNum: Double): Kilare = new Kilare(kMetresSqNum)

  implicit class SequSumEv[A](thisSeq: Sequ[A])
  {
    def sumBy(f: A => Area): Kilare =
    { var acc: Double = 0
      thisSeq.foreach(a => acc += f(a).kilareNum)
      Kilare(acc)
    }
  }
}

implicit class arraySumEv[A](thisArray: Array[A])
{
  def sumBy(f: A => Area): Kilare =
  {
    var acc: Double = 0
    thisArray.foreach(a => acc += f(a).kilareNum)
    Kilare(acc)
  }
}