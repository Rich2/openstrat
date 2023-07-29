/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb._

/* The alignment of text can be left right or centre. This may want to extend from a more general alignment trait. If such is useful. */
sealed trait TextAlign
{ def jsStr: String
  def attrib: XmlAtt
}

case object CenAlign extends TextAlign
{ def jsStr = "center"
  override val attrib: XmlAtt = TextAnchorAtt("middle")
}

case object LeftAlign extends TextAlign
{ def jsStr = "left"
  override val attrib: XmlAtt = TextAnchorAtt("start")
}
case object RightAlign extends TextAlign
{ def jsStr = "right"
  override val attrib: XmlAtt = TextAnchorAtt("end")
}

/** Baseline style for text. */
sealed trait BaseLine
{ def jsStr: String
}

/** Companion object for [[BaseLine]] trait, contains the object value instances of the trait. */
object BaseLine
{ case object Top extends BaseLine { def jsStr = "top" }
  case object Middle extends BaseLine { def jsStr = "middle" }
  case object Alphabetic extends BaseLine { def jsStr = "alphabetic" }
  case object Bottom extends BaseLine { def jsStr = "bottom" }

  /** Implemented as VPos.Top on JavaFx Canvas. */
  case object Hanging extends BaseLine { def jsStr = "hanging" }

  /** Implemented as geometry.VPos.BASELINE on JavaFx Canvas. */
  case object Ideographic extends BaseLine { def jsStr = "ideographic" }
}