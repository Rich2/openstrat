/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scalanative.unsafe._

package object pX11
{
  type Display = Ptr[CStruct2[CInt, CInt]]
  type Dummy = Ptr[CStruct2[CInt, CInt]]
  type Window = Ptr[CStruct11[Display, Dummy, CInt, CInt, CUnsignedInt, CUnsignedInt, CInt, CUnsignedInt, Dummy, CUnsignedLong, Dummy]]
}
