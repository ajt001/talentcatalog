resource "aws_security_group" "db" {
  name   = "${var.app}-${var.environment}-db-sg"
  vpc_id = var.vpc_id

  ingress {
    description = "Database endpoint port"
    protocol    = "tcp"
    from_port   = 3306
    to_port     = 3306
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}
