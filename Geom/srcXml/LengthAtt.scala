/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

/** A length attribute for CSS and SVG. */
trait LengthAtt extends XAttShort
{
  @targetName("multiply") def *(operand: Double): LengthAtt

  @targetName("divide") def /(operand: Double): LengthAtt
}

trait LengthCssAtt extends LengthAtt
{ /** The length value that can be moved between width and height. */
  def lengthVal: LenCss
}


/** XML attribute for height. */
trait HeightAtt extends  LengthAtt
{ override def name: String = "height"
  @targetName("multiply") def * (operand: Double): HeightAtt
  @targetName("divide") def / (operand: Double): HeightAtt
}

/** XML attribute for height. */
trait HeightCss extends  HeightAtt
{ @targetName("multiply") override def *(operand: Double): HeightCss
  @targetName("divide") override def /(operand: Double): HeightCss
}

case class HeightCent(num: Double) extends HeightSvg, HeightCss
{ override def valueStr: String = num.str + "%"
  @targetName("multiply") override def *(operand: Double): HeightCent = HeightCent(num * operand)
  @targetName("divide") override def /(operand: Double): HeightCent = HeightCent(num / operand)
}

/** XML attribute for height. */
trait HeightSvg extends HeightAtt

object HeightSvg
{ def apply(inp: Double): HeightSvg = new HeightSvgGen(inp)

  case class HeightSvgGen(num: Double) extends HeightSvg
  { override def valueStr: String = num.str
    @targetName("multiply") override def *(operand: Double): HeightSvg = HeightSvgGen(num * operand)
    @targetName("divide") override def /(operand: Double): HeightSvg = HeightSvgGen(num / operand)
  }
}