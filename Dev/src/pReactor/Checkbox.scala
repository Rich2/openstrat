package ostrat
package pReactor
import geom.{GraphicElems, _}, Colour._

/** Simple Checkbox with label **/
case class Checkbox(aIsSelected:Boolean = false, labelText:String = "", loc:Pt2 = 0 pp 0, aIsDisabled:Boolean = false, activeId:Any = "Checkbox") {
  val defaultSize = 12
  var isSelected = aIsSelected
  var isDisabled = aIsDisabled

  def put(aIsSelected: Boolean = isSelected, labelText: String = labelText, loc: Pt2 = loc, aIsDisabled: Boolean = isDisabled, activeId: Any = activeId): GraphicElems =
  { isSelected = aIsSelected
    isDisabled = aIsDisabled
    val ink = if (!isDisabled) White else Grey
    var ret:GraphicElems = Arr(TextGraphic(labelText, loc + (defaultSize pp 0), defaultSize, ink, LeftAlign))
    if (isSelected) ret = ret ++ Arr(Rect(defaultSize - 4, defaultSize - 4, loc).fill(ink))
    if (!isDisabled) ret ++ Arr(Rect(defaultSize, defaultSize, loc).drawActive(ink, 1, this))
    else ret ++ Arr(Rect(defaultSize, defaultSize, loc).draw(ink, 1))
  }

  def clicked() = if (!isDisabled) isSelected = !isSelected
}
