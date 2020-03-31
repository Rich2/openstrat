package ostrat
package pGrid

trait Tile
{ def r: Int
  def c: Int
}

case class HexTile(r: Int, c: Int) extends Tile
{
  def roord: Roord = Roord(r, c)
  override def toString: String = "Tile".appendParenthSemis(r.toString, c.toString)
  def adjOf(operand: Roord): OptRef[HTStep] = htSteps.optFind(_.roord == roord - operand)
}

class HTStep(val y: Int, c: Int)
{
  def roord: Roord = Roord(y, c)
}
object HTStepNone extends HTStep(0, 0)
class HTStepSome(yIn: Int, cIn: Int) extends HTStep(yIn, cIn)
object HTStepUR extends HTStepSome(2, 2)
object HTStepRt extends HTStepSome(0, 4)
object HTStepDR extends HTStepSome(-2, 2)
object HTStepDL extends HTStepSome(-2, -2)
object HTStepLt extends HTStepSome(0, -4)
object HTStepUL extends HTStepSome(2, -2)

trait TileMem[A]
{ val rd: Roord
  val value: A
}