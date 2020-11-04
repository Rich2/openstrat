// package ostrat
// package pReactor
// import geom._, pCanv._, Colour._

// /** Simple Checkbox */
// case class Checkbox(isSelected:Boolean = false, labelText:String = "", loc:Vec2 = 0 vv 0)
// { def put(canv: CanvasPlatform, aIsSelected:Boolean = isSelected, aLabelText:String = labelText, loc:Vec2 = loc): Unit =
//   { if (aIsSelected == true)
//     { canv.polygonFill(Rect(16, 16, loc), Pink)
//     } else 
//     { canv.polygonDraw(Rect(16, 16, loc), 1, Pink)
//     }
//     canv.textGraphic(aLabelText, 12, loc, Black, LeftAlign)
//   }
// }
