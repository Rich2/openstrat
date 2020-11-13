package ostrat
package pReactor
import geom.{GraphicElems, _}, Colour._

/** Simple Radio button with label **/
case class RadioOption(aIsSelected:Boolean = false, labelText:String = "", loc:Pt2 = 0 pp 0, aIsEnabled:Boolean = false, activeId: Any = "RadioOption")
{ val defaultSize = 12
  var isSelected = aIsSelected
  var isEnabled = aIsEnabled

  def put(aIsSelected: Boolean = isSelected, labelText: String = labelText, loc:Pt2 = loc, aIsEnabled: Boolean = isEnabled, activeId: Any = activeId): GraphicElems =
  { isSelected = aIsSelected
    isEnabled = aIsEnabled
    val ink = if (!isEnabled) White else Grey
    var ret:GraphicElems = Arr(TextGraphic(labelText, loc + (defaultSize pp 0), defaultSize, ink, LeftAlign))
    if (isSelected) ret = ret ++ Arr(Circle(defaultSize - 4, loc).fill(ink))

    //drawActive with lineWidth 0.01 FUDGE :( todo: flesh out   circle.active(activeId) and circle.drawActive(activeId)
    if (!isEnabled) ret ++ Arr(Rect(defaultSize, defaultSize, loc).drawActive(ink, 0.01, this), Circle(defaultSize, loc).draw(ink, 1))
    else ret ++ Arr(Circle(defaultSize, loc).draw(ink, 1))
  }
}
