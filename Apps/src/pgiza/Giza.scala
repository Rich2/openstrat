/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgiza
import geom.*, Colour.*

trait Pyramid
{ /** Base side length. */
  def sideLen: Length

  /** The 2-dimensional pyramid base. */
  def basePositioned: SqlignM2

  def baseFill: SqlignLen2Fill = basePositioned.fill(Wheat)

  def triFills(colour: Colour = Wheat) = RArr(basePositioned.diagTriangle0.fill(colour.darken(1.2)), basePositioned.diagTriangle1.fill(colour),
    basePositioned.diagTriangle2.fill(colour.lighten(1.4)), basePositioned.diagTriangle3.fill(colour))
  
  def diagsDraw: LineSegLen2ArrDraw = (basePositioned.sides ++ basePositioned.diags).draw()
  def baseGraphics: RArr[GraphicLen2Elem] = triFills() +% diagsDraw

  def offset: VecLen2
  val axisOffsetNum = 300
  def offVec = VecM2(axisOffsetNum, axisOffsetNum)
}

object GreatPyramid extends Pyramid
{
  override def basePositioned: SqlignM2 = SqlignM2.rb(sideLen, KhafrePyramid.basePositioned.rb.slate(offVec))
  override val sideLen: Metres = 230.3.metres
  def height = 146.6.metres

  override def offset: VecLen2 = VecM2(axisOffsetNum, axisOffsetNum)
}

object KhafrePyramid extends Pyramid
{ override def basePositioned: SqlignM2 = SqlignM2(sideLen)
  override val sideLen = 215.25.metres
  override def offset: VecLen2 = VecM2(0, 0)
}

object MenkaurePyramid extends Pyramid
{ override def basePositioned: SqlignM2 = SqlignM2.rb(sideLen, KhafrePyramid.basePositioned.rb.slate(-offVec))
  override def sideLen: Length = 102.2.metres
  override def offset: VecLen2 = VecM2(-axisOffsetNum, -axisOffsetNum)
}

object Giza
{
  def pyramids: RArr[Pyramid] = RArr(GreatPyramid, KhafrePyramid, MenkaurePyramid)
}