package ostrat
package pReactor
import geom._, pCanv._, Colour._

/** Simple Checkbox */
case class RadioOption(isSelected:Boolean = false, labelText:String = "", loc:Vec2 = 0 vv 0)
{ def put(canv: CanvasPlatform, aIsSelected:Boolean = isSelected, aLabelText:String = labelText, loc:Vec2 = loc): Unit =
  { if (aIsSelected == true)
    { canv.circleFill(Circle(12, loc), Pink)
    } else 
    { canv.circleDraw(Circle(12, loc), 1, Pink)
    }
    canv.textGraphic(aLabelText, 12, loc, Black, LeftAlign)
  }
}
