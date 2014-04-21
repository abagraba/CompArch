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
	.asciiz "[a]	"
string13:
	.asciiz "[b]	"
string14:
	.asciiz "[c]	"
string15:
	.asciiz "[d]	"
string16:
	.asciiz "[e]	"
string17:
	.asciiz "[f]	"
string18:
	.asciiz "[g]	"
string19:
	.asciiz "[h]	"
string20:
	.asciiz "[i]	"
string21:
	.asciiz "[j]	"
string22:
	.asciiz "[k]	"
string23:
	.asciiz "[l]	"
string24:
	.asciiz "[m]	"
string25:
	.asciiz "[n]	"
string26:
	.asciiz "[o]	"
string27:
	.asciiz "[p]	"
string28:
	.asciiz "[q]	"
string29:
	.asciiz "[r]	"
string30:
	.asciiz "[s]	"
string31:
	.asciiz "[t]	"
string32:
	.asciiz "[u]	"
string33:
	.asciiz "[v]	"
string34:
	.asciiz "[w]	"
string35:
	.asciiz "[x]	"
string36:
	.asciiz "[y]	"
string37:
	.asciiz "[z]	"
string38:
	.asciiz "[ ]	"
string39:
	.asciiz "You open your eyes."
string40:
	.asciiz "You appear to be in a large forest."
string41:
	.asciiz "You have no idea where you are."
string42:
	.asciiz "You see a large tree with a large wooden door embedded in the roots."
string43:
	.asciiz "You also see a small path meandering into the distance."
string44:
	.asciiz "Open the door."
string45:
	.asciiz "Wander down the path."
string46:
	.asciiz "Run around aimlessly."
string47:
	.asciiz "You open the door. It is very dark."
string48:
	.asciiz "You wander down the path. It is very dark."
string49:
	.asciiz "You run around aimlessly."
string50:
	.asciiz "You can barely see a thing..."
string51:
	.asciiz "You get eaten by a grue."
string52:
	.asciiz "You fall into a bottomless pit."
string53:
	.asciiz "You continue falling..."

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
		la	$a0, string39
		syscall
		la	$a0, string0
		syscall
		la	$a0, string40
		syscall
		la	$a0, string0
		syscall
		la	$a0, string41
		syscall
		la	$a0, string0
		syscall
		la	$a0, string0
		syscall
		ori		$s4, $s4, 1
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
		la	$a0, string42
		syscall
		la	$a0, string0
		syscall
		la	$a0, string43
		syscall
		la	$a0, string0
		syscall
		la	$a0, string0
		syscall
		la	$a0, string1
		syscall
		la	$a0, string44
		syscall
		la	$a0, string0
		syscall
		la	$a0, string2
		syscall
		la	$a0, string45
		syscall
		la	$a0, string0
		syscall
		la	$a0, string29
		syscall
		la	$a0, string46
		syscall
		la	$a0, string0
		syscall
		andi	$s4, $s4, 2147483646
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
		la	$a0, string47
		syscall
		la	$a0, string0
		syscall
		j		grue
jc0_2:
		la	$a0, string48
		syscall
		la	$a0, string0
		syscall
		j		grue
jr0:
		j		je0
je0:
		li	$v0, 4
		la	$a0, string49
		syscall
		la	$a0, string0
		syscall
		j		grue
########################################################################################
####									grue										####
########################################################################################
grue:
		la	$a0, string50
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
		la	$a0, string51
		syscall
		la	$a0, string0
		syscall
		la	$a0, string0
		syscall
########################################################################################
####									bottomless									####
########################################################################################
bottomless:
		la	$a0, string52
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
fall:
		la	$a0, string53
		syscall
		la	$a0, string0
		syscall
		la	$a0, string10
		syscall
		addi	$t9, $0, 32
input4:
		li	$v0, 12
		syscall
		beq		$t9, $v0, input4_end
		li	$v0, 4
		la	$a0, string0
		syscall
		j		input4
input4_end:
		li	$v0, 4
		la	$a0, string0
		syscall
		j		fall
########################################################################################
####									ERROR										####
########################################################################################
error:
		la	$a0, string11
		syscall
