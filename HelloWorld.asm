.data
dollars:	.word	0
yen:	.word	0
bitcoins:	.word	0

 
.text
main: 
addi 	$t0, 	$zero, 	400
sw	$t0, 	dollars
addi	$v0,   $zero,    10
syscall
