package ostrat
package pEarth
import pGrid._

trait E80Data
{
  implicit val grid: HexGridIrr
  def terrs: TilesRef[WTile]
  val sTerrs: SideBooleans
}

object EuropeNWGrid extends HexGridIrr(446, EGrid80Km.getBounds(200, 446, 540))
object EuropeNEGrid extends HexGridIrr(446, EGrid80Km.getBounds(400, 446, 540))