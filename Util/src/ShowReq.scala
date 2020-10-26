/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

sealed trait ShowReq

object ShowStdReq extends ShowReq
object ShowSemiReq extends ShowReq
object ShowCommaReq extends ShowReq
object ShowTyped extends ShowReq