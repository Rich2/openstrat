/* Copyright 2018-21 Richard Oliver, W0d. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

object Star5
{
  val classicRatio = 0.382

  def apply(ratio: Double = classicRatio): Polygon =
  { val l2: Pt2s = Pt2s(Pt2(0, 1), Pt2(0, ratio).rotateDegs(36))
    iToFlatMap(0, 4)(i => l2.rotate(-Deg72 * i)).toPolygon
  }

  def fill(colour: Colour, ratio: Double = classicRatio): PolygonFill = apply(ratio).fill(colour)
  
  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().draw(colour, lineWidth)

  def crossLines(ratio: Double = classicRatio): GraphicElem =
  { val poly = apply(ratio)
    Ints(2, 6, 10, 4, 8, 2).map{i => poly.vert(i) }.toPathDraw(1)
  }

  /** Not quite sure what this does. */
  def ptUpYCentred(ratio: Double = classicRatio): Polygon = apply(ratio).slateY(Deg36.cos / 2 - 0.5)
}

object Star7
{
  val classicRatio = 0.692

  def apply(ratio: Double = classicRatio): Polygon =
  { val l2: Pt2s = Pt2s(Pt2(0, 1), Pt2(0, ratio).rotateClkDegs(180 / 7))
    iToFlatMap(0, 6)(i => l2.rotate(AngleVec(-360 / 7) * i)).toPolygon
  }
  
  def fill(colour: Colour, ratio: Double = classicRatio): PolygonFill = apply(ratio).fill(colour)
}

object Star3
{
  def apply(sideLength: Double = 1): PolygonGen = PolygonGen((0 pp sideLength * math.sqrt(3) / 3),
              (sideLength / 2 pp -sideLength * math.sqrt(3) / 6),
              (-sideLength / 2 pp -sideLength * math.sqrt(3) / 6))
      
  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().draw(colour, lineWidth)

  def fill(colour: Colour): PolygonFill = apply().fill(colour)
}

object Pentagram
{
  def apply(): PolygonGen =
  { val l2: Pt2s = Pt2s(Pt2(0, 1), Pt2(0, -1).rotateClkDegs(36))
    iToFlatMap(0, 4)(i => l2.rotate(-Deg72 * i)).toPolygon
  }

  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().draw(colour, lineWidth)
}