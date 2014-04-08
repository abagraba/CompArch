.data
########################################################################################
####							String Allocation									####
########################################################################################
string0:
	.asciiz "\n"
string1:
	.asciiz "[1]	"
string2:
	.asciiz "[2]	"
string3:
	.asciiz "[3]	"
string4:
	.asciiz "[4]	"
string5:
	.asciiz "[5]	"
string6:
	.asciiz "[6]	"
string7:
	.asciiz "[7]	"
string8:
	.asciiz "[8]	"
string9:
	.asciiz "[9]	"
string10:
	.asciiz "Press space to continue...\n"
string11:
	.asciiz "ERROR!!!"
string12:
	.asciiz "You open your eyes."
string13:
	.asciiz "You appear to be in a large forest."
string14:
	.asciiz "You have no idea where you are."
string15:
	.asciiz "You see a large tree with a large wooden door embedded in the roots."
string16:
	.asciiz "You also see a small path meandering into the distance."
string17:
	.asciiz "Open the door."
string18:
	.asciiz "Wander down the path"
string19:
	.asciiz "You open the door. It is very dark."
string20:
	.asciiz "You wander down the path. It is very dark."
string21:
	.asciiz "You can barely see a thing..."
string22:
	.asciiz "You get eaten by a grue."
string23:
	.asciiz "You fall into the bottomless pit."

########################################################################################
####								Jump Tables										####
########################################################################################
jt0:
	.word jc0_1, jc0_2, jr0

.text
.globl	main
########################################################################################
####									main										####
########################################################################################
main:
		li	$v0, 4
		la	$a0, string12
		syscall
		la	$a0, string0
		syscall
		la	$a0, string13
		syscall
		la	$a0, string0
		syscall
		la	$a0, string14
		syscall
		la	$a0, string0
		syscall
		la	$a0, string0
		syscall
		la	$a0, string10
		syscall
		addi	$t9, $0, 32
input0:
		li	$v0, 12
		syscall
		beq		$t9, $v0, input0_end
		li	$v0, 4
		la	$a0, string0
		syscall
		j		input0
input0_end:
		li	$v0, 4
		la	$a0, string0
		syscall
		j		start
########################################################################################
####									start										####
########################################################################################
start:
		la	$a0, string15
		syscall
		la	$a0, string0
		syscall
		la	$a0, string16
		syscall
		la	$a0, string0
		syscall
		la	$a0, string0
		syscall
		la	$a0, string1
		syscall
		la	$a0, string17
		syscall
		la	$a0, string0
		syscall
		la	$a0, string2
		syscall
		la	$a0, string18
		syscall
		la	$a0, string0
		syscall
input1:
		li		$t0, 2					# $t0 = Upper Bound of input.
		li	$v0, 12
		syscall
		add		$s1, $v0, $0			# s1 = input
		addi	$v0, $v0, -49
		bltz	$v0, input1_x			# Jump to input1_x if $v0 < 0.
		sub		$t1, $v0, $t0
		bgez	$t1, input1_x			# Jump to input1_x if $v0 > max Input.
		add		$s0, $t0, $t1			# $s0 = [0, maxInput - 1]
		j		input1_end
input1_x:
		add		$s0, $t0, $0			# $s0 = maxInput
input1_end:
		li	$v0, 4
		la	$a0, string0
		syscall
		sll		$t0, $s0, 2
		lw		$t0, jt0($t0)
		jr		$t0
jc0_1:
		la	$a0, string19
		syscall
		la	$a0, string0
		syscall
		j		je0
jc0_2:
		la	$a0, string20
		syscall
		la	$a0, string0
		syscall
		j		je0
jr0:
		j		input1
je0:
		j		grue
########################################################################################
####									grue										####
########################################################################################
grue:
		li	$v0, 4
		la	$a0, string21
		syscall
		la	$a0, string0
		syscall
		la	$a0, string10
		syscall
		addi	$t9, $0, 32
input2:
		li	$v0, 12
		syscall
		beq		$t9, $v0, input2_end
		li	$v0, 4
		la	$a0, string0
		syscall
		j		input2
input2_end:
		li	$v0, 4
		la	$a0, string0
		syscall
		la	$a0, string22
		syscall
		la	$a0, string0
		syscall
		la	$a0, string0
		syscall
########################################################################################
####									bottomless									####
########################################################################################
bottomless:
		la	$a0, string23
		syscall
		la	$a0, string0
		syscall
		la	$a0, string10
		syscall
		addi	$t9, $0, 32
input3:
		li	$v0, 12
		syscall
		beq		$t9, $v0, input3_end
		li	$v0, 4
		la	$a0, string0
		syscall
		j		input3
input3_end:
		li	$v0, 4
		la	$a0, string0
		syscall
		j		bottomless
########################################################################################
####									ERROR										####
########################################################################################
error:
		la	$a0, string1
		syscall
