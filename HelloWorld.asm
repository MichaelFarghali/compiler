.data
dollars:	.word	0
yen:	.word	0
bitcoins:	.word	0

 
.text
main: 
addi 	$t0, 	$zero, 	100
sw	$t0, 	dollars
lw 	$t0,    dollars
addi 	$t1, 	$zero, 	100
sle	$t0,	$t0,	$t1
beq	$t0,    $0,    elseif0
addi 	$t2, 	$zero, 	25
sw	$t2, 	yen
j	endif0
elseif0:
addi 	$t0, 	$zero, 	30
sw	$t0, 	bitcoins
endif0:
addi	$v0,   $zero,    10
syscall
