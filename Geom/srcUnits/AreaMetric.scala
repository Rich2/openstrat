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

/** Square [[Picometres]] a measure of [[Area]]. [[Picares]] follows the same naming convention as [[Hectares]]. */
class Picares(val picaresNum: Double) extends AnyVal with AreaMetric
{ override def typeStr: String = "Picares"
  override def unitsDbl: Double = picaresNum
  override def endingStr: String = "pm²"
  override def +(operand: Area): Picares = Picares(picaresNum + operand.picaresNum)
  override def -(operand: Area): Picares = Picares(picaresNum - operand.picaresNum)
  override def *(operand: Double): Picares = new Picares(picaresNum * operand)
  override def /(operand: Double): Picares = new Picares(picaresNum / operand)
  @inline override def millaresNum: Double = picaresNum * 1e-18
  @inline override def metraresNum: Double = picaresNum * 1e-24
  @inline override def hectaresNum: Double = picaresNum * 1e-28
  @inline override def kilaresNum: Double = picaresNum * 1e-30
  override def compare(that: Area): Int = if (picaresNum > that.picaresNum) 1 else if (picaresNum < that.picaresNum) -1 else 0
}

/** Square millimetres a measure of [[Area]]. [[Millares]] follows the same naming convention as [[Hectares]]. */
class Millares(val millaresNum: Double) extends AnyVal with AreaMetric {
  override def typeStr: String = "Millares"
  override def unitsDbl: Double = millaresNum
  override def endingStr: String = "mm²"
  override def +(operand: Area): Millares = Millares(millaresNum + operand.millaresNum)
  override def -(operand: Area): Millares = Millares(millaresNum - operand.millaresNum)
  override def *(operand: Double): Millares = new Millares(millaresNum * operand)
  override def /(operand: Double): Millares = new Millares(millaresNum / operand)
  @inline override def picaresNum: Double = millaresNum * 1e18
  @inline override def metraresNum: Double = millaresNum * 1e-6
  @inline override def hectaresNum: Double = millaresNum * 1e-10
  @inline override def kilaresNum: Double = millaresNum * 1e-12
  override def compare(that: Area): Int = if (millaresNum > that.millaresNum) 1 else if (millaresNum < that.millaresNum) -1 else 0
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
  @inline override def picaresNum: Double = metraresNum * 1e24
  @inline override def millaresNum: Double = metraresNum * 1e6
  @inline override def hectaresNum: Double = metraresNum * 1e-4
  @inline override def kilaresNum: Double = metraresNum * 1e-6  
  override def compare(that: Area): Int = if(kilaresNum > that.kilaresNum) 1 else if (kilaresNum < that.kilaresNum) -1 else 0
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
  @inline override def picaresNum: Double = hectaresNum * 1e28
  @inline override def millaresNum: Double = hectaresNum * 1e10
  @inline override def metraresNum: Double = hectaresNum * 1e4
  @inline override def kilaresNum: Double = hectaresNum * 1e-2   
  override def compare(that: Area): Int = if(kilaresNum > that.kilaresNum) 1 else if (kilaresNum < that.kilaresNum) -1 else 0
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
  @inline override def picaresNum: Double = kilaresNum * 1e30
  @inline override def millaresNum: Double = kilaresNum * 1e12
  @inline override def metraresNum: Double = kilaresNum * 1e6
  @inline override def hectaresNum: Double = kilaresNum * 100  
  override def compare(that: Area): Int = if(kilaresNum > that.kilaresNum) 1 else if (kilaresNum < that.kilaresNum) -1 else 0
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

/** Class has an area property. */
trait WithKilares
{ /** The area of the entity this object represents */
  def area: Kilares
}