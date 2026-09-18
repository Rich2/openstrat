/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

enum UserStatus(val str: String)
{ case User extends UserStatus("User")
  case Admin extends UserStatus("Admin")
}