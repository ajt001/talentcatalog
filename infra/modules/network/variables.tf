variable "app" {
  description = "Name of the application"
  type = string
}

variable "environment" {
  description = "Name of the application environment. e.g. dev, prod, test, staging"
  type = string
}


variable "vpc_cidr" {
  description = "The CIDR block for the VPC"
  default     = "172.32.0.0/16"
}

variable "private_subnet_cidr" {
  description = "List of CIDR blocks for private subnets"
  default     = ["172.32.0.0/20", "172.32.16.0/20", "172.32.32.0/20"]
}

variable "public_subnet_cidr" {
  description = "List of CIDR blocks for public subnets"
  default     = ["172.32.48.0/20", "172.32.64.0/20", "172.32.80.0/20"]
}

variable "availability_zones" {
  description = "List of availablility zones"
  default     = ["us-east-1a", "us-east-1b", "us-east-1c"]
}