<template>
  <div class="number-picker">
    <div class="number-btn" @click.stop="reduce">-</div>
    <input v-model="count" class="md-input"/>
    <div class="number-btn" @click.stop="increase">+</div>
  </div>
</template>
<script>
export default {
  props: {
    value: [Number, String],
    step: { type: Number, default: 1 },
    max: Number,
    min: Number
  },
  model: { prop: 'value', event: 'change' },
  data() {
    return {
      count: 0
    }
  },
  watch: {
    value: {
      handler(v) {
        if (typeof v !== 'undefined' && v !== this.count) {
          this.count = v || 0
        }
      },
      immediate: true
    }
  },
  methods: {
    reduce() {
      if (this.count <= this.min ) {
        this.count = this.min
      } else {
        this.count -= this.step
      }
      this.$emit('change', this.count)
      this.$emit('reduce', this.count)
    },
    increase() {
      if (this.count >= this.max ) {
        this.count = this.min
      } else {
        this.count += this.step
      }
      this.$emit('increase', this.count)
      this.$emit('change', this.count)
    }
  }
}
</script>
<style lang="scss" scoped>
$borderStyle: 1px solid gray;
.number-picker {
  display: flex;
  height: 40px;
  justify-content: center;
  align-content: center;
  .number-btn {
    cursor: pointer;
    width: 40px;
    height: 40px;
    display: flex;
    line-height: 40px;
    justify-content: center;
    align-content: center;
    border: $borderStyle;
  }
  ::v-deep {
    input {
      text-align: center;
      border-left: none;
      border-right: none;
      border-top: $borderStyle;
      border-bottom: $borderStyle;
    }
  }
}
</style>
