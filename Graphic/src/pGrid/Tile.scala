package ostrat
package pGrid

trait Tile
{ def y: Int
  def c: Int
}

case class HexTile(y: Int, c: Int) extends Tile
{
  def roord: Roord = Roord(y, c)
  override def toString: String = "Tile".appendParenthSemis(y.toString, c.toString)
  def adjORCenOf(operand: Roord): OptRef[HTStepLike] = htSteps.optFind(_.roord == roord - operand)
  def adjOf(operand: Roord): OptRef[HTStep] = htStepSomes.optFind(_.roord == roord - operand)
}

case class HTileAndStep(y1: Int, c1: Int, step: HTStep)
{
  def r1: Roord = Roord(y1, c1)
  def r2: Roord = r1.step(step)
}

/** Takes 7 values, the 6 HTStep values and the HTStepNone value. */
class HTStepLike(val y: Int, c: Int)
{ def roord: Roord = Roord(y, c)
}
case object HTStepNone extends HTStepLike(0, 0)

/** A Hex tile Step can take 6 values */
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