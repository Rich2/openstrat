/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait QuadLen2[+VT <: PtLen2] extends PolygonLen2P4[VT]
{

}

trait QuadM2 extends QuadLen2[PtM2], PolygonM2P4