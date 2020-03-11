package ostrat
package gOne
import pCanv._, geom._

case class GOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Gui")
{
  repaints(Rectangle(2).scale(200).fill(Colour.Green))
}