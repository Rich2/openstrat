/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A [[Polygon]] with at least 3 vertices. The PolygonNPlus traits include values for the vertices and the x and y components of the vertices. The X
 * and Y components are included because Graphics implementation APIs use them. */
trait Polygon3Plus extends Polygon
{
  /** The X component of the 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x1: Double

  /** The Y component of the 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y1: Double

  /** The 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v1: Pt2

  /** The X component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x2: Double

  /** The Y component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y2: Double

  /** The 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v2: Pt2

  /** The X component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x3: Double

  /** The Y component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y3: Double

  /** The 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   *  12 o'clock. */
  def v3: Pt2

  /** The last vertex. */
  override def vLast: Pt2 = vert(vertsNum)

  /** Polygon side 1 from the last vertex to Vertex 1. */
  def sd1: LineSeg = LineSeg(vLast, v1)

  /** Polygon side 2 from vertex 1 to vertex 2. */
  def sd2: LineSeg = LineSeg(v1, v2)

  /** Polygon side 3 from v2 to v3. */
  def sd3: LineSeg = LineSeg(v2, v3)

  /** The centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can be thought of as
   *  vertex 0.5. */
  def sd1Cen: Pt2

  /** The X component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   *  be thought of as vertex 0.5. */
  def xSd1Cen: Double

  /** The Y component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   *  be thought of as vertex 0.5. */
  def ySd1Cen: Double

  /** The centre or half way point of side 2 of this polygon. Side 2 starts at the v1 vertex and ends at the v2 vertex. This can be thought of as
   * vertex 1.5 */
  def sd2Cen: Pt2

  /** The X component of the centre or half way point of side 2 of this polygon. Side 2 starts at the v1 vertex and ends at the v1 vertex. This can be
   *  thought of as vertex 1.5. */
  def xSd2Cen: Double

  /** The Y component of the centre or half way point of side 3 of this polygon. Side 2 starts at the v1 vertex and ends at the v2 vertex. This can be
   *  thought of as vertex 1.5. */
  def ySd2Cen: Double

  /** The centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be thought of as
   *  vertex 2.5. */
  def sd3Cen: Pt2

  /** The X component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
   *  thought of as vertex 2.5. */
  def xSd3Cen: Double

  /** The Y component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
   *  thought of as vertex 2.5. */
  def ySd3Cen: Double
}

/** A [[Polygon]] with at least 4 vertices. */
trait Polygon4Plus extends Polygon3Plus
{
  /** The X component of the 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x4: Double
  /** The Y component of the 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y4: Double
  /** The 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   *  12 o'clock. */
  def v4: Pt2

  /** The centre or half way point of side 4 of this polygon. Side 4 starts at the v3 vertex and ends at the v4 vertex. This can be thought of as
   * vertex 3.5. */
  def sd4Cen: Pt2

  /** The X component of the centre or half way point of side 4 of this polygon. Side 4 starts at the v3 vertex and ends at the v4 vertex. This can be
   *  thought of as vertex 3.5. */
  def xSd4Cen: Double

  /** The Y component of the centre or half way point of side 4 of this polygon. Side 4 starts at the v3 vertex and ends at the v4 vertex. This can be
   *  thought of as vertex 3.5. */
  def ySd4Cen: Double
}

/** A [[Polygon]] with at least 5 vertices. */
trait Polygon5Plus extends Polygon4Plus
{
  /** The X component of the 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x5: Double

  /** The Y component of the 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y5: Double

  /** The 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v5: Pt2
}

/** A [[Polygon]] with at least 6 vertices. */
trait Polygon6Plus extends Polygon5Plus
{
  /** The X component of the 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x6: Double

  /** The Y component of the 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y6: Double

  /** The 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v6: Pt2
}