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
  def adjORCenOf(operand: Roord): OptRef[HTStepLike] = htSteps.optFind(_.roord == roord - operand)
  def adjOf(operand: Roord): OptRef[HTStep] = htStepSomes.optFind(_.roord == roord - operand)
}

class HTStepLike(val y: Int, c: Int)
{ def roord: Roord = Roord(y, c)
}
case object HTStepNone extends HTStepLike(0, 0)
class HTStep(yIn: Int, cIn: Int) extends HTStepLike(yIn, cIn)
case object HTStepUR extends HTStep(2, 2)
case object HTStepRt extends HTStep(0, 4)
case object HTStepDR extends HTStep(-2, 2)
case object HTStepDL extends HTStep(-2, -2)
case object HTStepLt extends HTStep(0, -4)
case object HTStepUL extends HTStep(2, -2)

trait TileMem[A]
{ val rd: Roord
  val value: A
}