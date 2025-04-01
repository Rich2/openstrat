/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgiza
import geom.*, Colour.*

trait Pyramid
{
  def sideLen: Length

  /** The  */
  def basePositioned: SqlignM2
  def baseFill: SqlignLen2Fill = basePositioned.fill(Wheat)
  def lbrtLine: LineSegLen2Draw = basePositioned.lbrtDiag.draw()

  def offset: VecLen2
  val axisOffsetNum = 300
  def offVec = VecM2(axisOffsetNum, axisOffsetNum)
}

object GreatPyramid extends Pyramid
{
  override def basePositioned: SqlignM2 = SqlignM2.rb(sideLen, KhafrePyramid.basePositioned.rb.slate(offVec))
  override val sideLen = 230.3.metres
  def height = 146.6.metres

  override def offset: VecLen2 = VecM2(axisOffsetNum, axisOffsetNum)
}

object KhafrePyramid extends Pyramid
{
  override def basePositioned: SqlignM2 = SqlignM2(sideLen)
  override val sideLen = 215.25.metres
  override def offset: VecLen2 = VecM2(0, 0)
}

object MenkaurePyramid extends Pyramid
{
  override def basePositioned: SqlignM2 = SqlignM2.rb(sideLen, KhafrePyramid.basePositioned.rb.slate(-offVec))
  override def sideLen: Length = 102.2.metres
  override def offset: VecLen2 = VecM2(-axisOffsetNum, -axisOffsetNum)
}

object Giza
{
  def pyramids = RArr(GreatPyramid, KhafrePyramid, MenkaurePyramid)
}