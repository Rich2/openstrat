/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Css margin declaration. */
case class MarginDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin"
}

/** CSS margin = 0 declaration. */
object Margin0Dec extends MarginDec(CssVal("0"))

/** CSS margin-top declaration. */
case class MarginTopDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin-top"
}

/** CSS margin-bottom declaration. */
case class MarginBottomDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin-bottom"
}

/** CSS margin-left declaration. */
case class MarginLeftDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin-left"
}

/** CSS margin-right declaration. */
case class MarginRightDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin-right"
}

/** CSS border declaration. */
case class BorderDec(value: CssVal) extends CssDecStd
{ override def property: String = "border"
}

/** CSS margin-lop and bottom declarations. */
case class MarginTBDec(value: CssVal) extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(MarginTopDec(value), MarginBottomDec(value))
}

/** CSS margin-left and margin-right declarations set to same value. */
case class MarginLRDec(value: CssVal) extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(MarginLeftDec(value), MarginRightDec(value))
}

/** CSS margin-left and margin-right declarations set to auto. */
case object MarginLRAutoDec extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(MarginLeftDec(AutoCss), MarginRightDec(AutoCss))
}

object BorderDec
{ /** Factory apply method for CSS border declaration */
  def apply(valuesStr: String): BorderDec = new BorderDec(CssVal(valuesStr))
}

/** CSS border = none declaration. */
object BorderNoneDec extends MarginDec(CssVal("none"))

/** CSS border-style value. */
trait BorderStyle extends CssVal
{ /** apply method combines border style with colour." */
  def apply(colour: Colour): CssVal = CssVal(str -- colour.webStr)
}

/** CSS solid border style value. */
object SolidCss extends BorderStyle
{ override def str: String = "solid"
}

/** CSS padding declaration. */
case class PaddingDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding"
}

/** CSS padding = 0 declaration. */
object Padding0Dec extends PaddingDec(CssVal("0"))

/** CSS padding-left declaration. */
case class PadLeftDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding-left"
}

/** CSS padding-right declaration. */
case class PadRightDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding-right"
}

/** CSS padding-top declaration. */
case class PadTopDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding-top"
}
/** CSS padding-bottom declaration. */
case class PadBottomDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding-bottom"
}